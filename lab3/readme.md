# Questões 3.1
## a) Identify a couple of examples that use AssertJ expressive methods chaining.
### **A_EmployeeRepositoryTest.java**
```java
assertThat( found ).isEqualTo(alex); // l.37
assertThat(fromDb).isNull(); // l.43 l.52 l.59
assertThat(fromDb.getEmail()).isEqualTo( emp.getEmail()); // l.53
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName()); // l.75
```
### **B_EmployeeRepositoryTest.java**
```java
assertThat(found.getName()).isEqualTo(name); // l.63
assertThat(fromDb).isNull(); // l.69 l.102
assertThat(doesEmployeeExist).isEqualTo(true); // l.77 l.85
assertThat(fromDb.getName()).isEqualTo("john"); // l.93
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName()); // l.113
```
### **D_EmployeeRepositoryTest.java**
```java
assertThat(found).extracting(Employee::getName).containsOnly("bob"); // l.58
```
### **E_EmployeeRepositoryTest.java**
```java
assertThat(found).extracting(Employee::getName).containsOnly("bob"); // l.53
assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); // l.66
assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex"); // l.67
```

## b) Identify an example in which you mock the behavior of the repository (and avoid involving a database).

No ficheiro B_EmployeeService_UnitTest.java podemos observar que o comportamento de EmployeeRepository é mockado, sendo este configurado no @BeforeEach.
```java
@BeforeEach
public void setUp() {
    //these expectations provide an alternative to the use of the repository
    Employee john = new Employee("john", "john@deti.com");
    john.setId(111L);
    Employee bob = new Employee("bob", "bob@deti.com");
    Employee alex = new Employee("alex", "alex@deti.com");
    List<Employee> allEmployees = Arrays.asList(john, bob, alex);
    Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
    Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
    Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
    Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
    Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
    Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
}
```

## c) What is the difference between standard @Mock and @MockBean?

O @Mock é usado na framework de mock Mockito, pode ser utilizado com Sping Framework e não precisa de configuração prévia. Geralmente é utilizado para testes unitários e serve para testar uma classe em específico. Não precisa de dependências adicionais para além do Mockito. 

O @MockBean é usado na ferramenta de teste do Spring Framework não pode ser usado fora dele e precisa de configuração própria da framework. Geralmente é usado para testes de integração, testando interações entre diferentes componentes, e tem como propósito testar a aplicação como um todo. Para além do Spring Framework também precisa das suas dependências de teste para funcionar corretamente.

## d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?

O ficheiro "application-integrationtest.properties" é utilizado para fazer alterações opcionais às propriedades de configuração dos testes de integração em Spring Framework

## The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?

Na estratégia C o controlador REST é testado isoladamente, usa o objeto do tipo MockMvc para simular as requisições HTTP, o @MockBean cria um objeto "mockable" simulando os métodos sem uma base de dados real e é um tipo de testes mais leve e rápido que permite isolar as camadas de Service e Controller

Na estratégia D a anotação @SpringBootTest carrega a aplicação inteira, o MockMvc desempenha a mesma função que na estratégia C, usa a anotação @AutoConfigureTestDatabase para configurar automaticamente a base de dados para realizar testes, que limpa é limpa depois de cada iteração, e é um tipo de testes mais lento e pesado mas que por carregar a aplicação completa e usar uma base de dados real permite garantir se as camadas da aplicação funcionam todas bem em conjunto

A estratégia E é toda ela muito semlhante à D com a diferença de que ao invés de utilizar um objeto do tipo MockMvc usa um objeto TestRestTemplate que realiza requisições reais à API, permitindo testar toda a aplicação e permitindo verificar a integração com outras API's ou sistemas externos