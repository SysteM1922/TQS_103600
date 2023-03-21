package com.example.connection;

import java.io.IOException;

/**
 *
 * @author ico
 */
public interface ISimpleHttpClient {
    
    public String doHttpGet(String url) throws IOException;
}
