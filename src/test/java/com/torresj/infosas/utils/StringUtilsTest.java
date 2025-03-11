package com.torresj.infosas.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringUtilsTest {
    @Test
    void obfuscateDni(){
        String dni = "12345678A";
        String obfuscatedDni = "***4567**";
        Assertions.assertEquals(obfuscatedDni, StringUtils.obfuscateDni(dni));
    }

    @Test
    void obfuscateDniNull(){
        String dni = null;
        String obfuscatedDni = "";
        Assertions.assertEquals(obfuscatedDni, StringUtils.obfuscateDni(dni));
    }

    @Test
    void obfuscateDniEmpty(){
        String dni = "";
        String obfuscatedDni = "";
        Assertions.assertEquals(obfuscatedDni, StringUtils.obfuscateDni(dni));
    }

    @Test
    void obfuscateDniLessThanFive(){
        String dni = "12345";
        String obfuscatedDni = "";
        Assertions.assertEquals(obfuscatedDni, StringUtils.obfuscateDni(dni));
    }
}