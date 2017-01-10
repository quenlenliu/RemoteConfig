package com.ziwa.remoteconfig;

/**
 * Created by alex on 17-1-6.
 */

public interface IFuleBaseRemoteConfigValue {
    int asInt();
    long asLong();
    float asFloat();
    double asDouble();
    boolean asBoolean();
    byte[] asByteArray();
    String asString();
    int getSource();
}
