package com.example.geocoding;

import com.example.connection.ISimpleHttpClient;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {

    @InjectMocks
    private AddressResolver resolver;

    @Mock
    private ISimpleHttpClient client;

    private final String response = "{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"® 2022 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"® 2022 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.633116,\"lng\":-8.658784}},\"locations\":[{\"street\":\"Avenida João Jacinto de Magalhães\",\"adminArea6\":\"Aveiro\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Aveiro\",\"adminArea5Type\":\"City\",\"adminArea4\":\"Aveiro\",\"adminArea4Type\":\"County\",\"adminArea3\":\"\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3810-149\",\"geocodeQualityCode\":\"B1AAA\",\"geocodeQuality\":\"STREET\",\"dragPoint\":false,\"sideOfStreet\":\"L\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.63312,\"lng\":-8.65873},\"displayLatLng\":{\"lat\":40.63312,\"lng\":-8.65873},\"mapUrl\":\"\"}]}]}";
    private final String bad_response = "{\"info\":{\"statuscode\":400,\"copyright\":{\"text\":\"© 2022 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"© 2022 MapQuest, Inc.\"},\"messages\":[\"Illegal argument from request: Invalid LatLng specified.\"]},\"options\":{\"maxResults\":1,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{},\"locations\":[]}]}";

    @Test
    void whenResolveDetiGps_returnJacintoMagalhaeAddress() throws ParseException, IOException, URISyntaxException {

        Mockito.when(client.doHttpGet(anyString())).thenReturn(response);

        Optional<Address> result = resolver.findAddressForLocation(40.633116,-8.658784);

        assertEquals(result.get(), new Address("Avenida João Jacinto de Magalhães", "Aveiro", "", "3810-149", null));

        verify(client).doHttpGet(anyString());
    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {

        Mockito.when(client.doHttpGet(anyString())).thenReturn(bad_response);

        Optional<Address> result = resolver.findAddressForLocation(-300, -810);
        
        assertFalse(result.isPresent());

        verify(client).doHttpGet(anyString());

    }
}