package com.enggvam.countryselectorapp.data.remote;

/**
 * <h1>Enum for backend errors</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
public enum BackendError {
    /**
     * Invalid backend error.(request format is incorrect)
     */
    INVALID_REQUEST, //invalid request
    /**
     * Unsuccessful backend error.(api route not found)
     */
    NOT_FOUND_ERROR, //route not found
    /**
     * Parsing backend error.(json to object/model parsing error)
     */
    PARSING_ERROR, // json to object parsing error
    /**
     * Network backend error. (no network connection)
     */
    NETWORK_ERROR; // no network connection
}
