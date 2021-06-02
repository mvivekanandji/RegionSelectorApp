package com.enggvam.countryselectorapp.data.remote.response;

import java.util.Objects;

/**
 * <h1>POJO class representing the core part of a backend response (currently just the code)</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
public class BackendResponse {
    private int code;

    public BackendResponse(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BackendResponse that = (BackendResponse) o;
        return code == that.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "BackendResponse{" +
                "code=" + code +
                '}';
    }
}
