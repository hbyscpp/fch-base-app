package com.seaky.fch.open.api.service;

import com.googlecode.jsonrpc4j.JsonRpcClient;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class NodeRpcService {

    @Autowired
    @Qualifier("fchRpcClient")
    private JsonRpcHttpClient fchJsonRpcHttpClient;



}
