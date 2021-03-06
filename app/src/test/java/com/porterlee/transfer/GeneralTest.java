package com.porterlee.transfer;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static com.porterlee.transfer.Utils.SHA1;
import static com.porterlee.transfer.Utils.byteToHexChar;
import static com.porterlee.transfer.Utils.bytesToHex;
import static org.junit.Assert.*;

/**
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GeneralTest {
    @Test
    public void plcBarcodeNoThrowOnPartialBarcode() {
        String barcode = "";
        PlcBarcode.Encoding encoding = null;

        if (BuildConfig.barcodeType_item_base32Prefix != null) {
            encoding = PlcBarcode.Encoding.Base32;
            barcode += BuildConfig.barcodeType_item_base32Prefix;
        } else if (BuildConfig.barcodeType_item_base64Prefix != null) {
            encoding = PlcBarcode.Encoding.Base64;
            barcode += BuildConfig.barcodeType_item_base64Prefix;
        } else if (BuildConfig.barcodeType_item_genericPrefixes != null) {
            encoding = null;
            barcode += BuildConfig.barcodeType_item_genericPrefixes[0];
        }

        if (BuildConfig.barcodeType_item_hasLabCode) {
            barcode += "LAB";
        }

        if (encoding != null) {
            switch (encoding) {
                case Base32:
                    barcode += "9N7A";
                    break;
                case Base64:
                    barcode += "k8mZq";
                    break;
            }
        } else {
            barcode += "ECN0";
        }

        for (int i = 0; i < barcode.length() + 1; i++) {
            new PlcBarcode(barcode.substring(0, i));
        }
    }

    @Test
    public void separatedValues_test() {
        String appId = "com.porterlee.ems.transfer";
        String expected = "ems.transfer";
        String actual = appId.substring(appId.indexOf('.', appId.indexOf('.') + 1) + 1);
        assertEquals(expected, actual);
    }

    @Test
    public void csvContainsInt_test() {
        assertTrue(Utils.csvContainsInt("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20", 1));
        assertTrue(Utils.csvContainsInt("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20", 5));
        assertTrue(Utils.csvContainsInt("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20", 17));
        assertTrue(Utils.csvContainsInt("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20", 20));
        assertEquals(Utils.csvContainsInt("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20", 100), false);
        assertEquals(Utils.csvContainsInt("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20", 56874), false);
    }

    @Test
    public void testByteToHexChar() {
        assertEquals('0', byteToHexChar((byte) 0));
        assertEquals('1', byteToHexChar((byte) 1));
        assertEquals('2', byteToHexChar((byte) 2));
        assertEquals('3', byteToHexChar((byte) 3));
        assertEquals('4', byteToHexChar((byte) 4));
        assertEquals('5', byteToHexChar((byte) 5));
        assertEquals('6', byteToHexChar((byte) 6));
        assertEquals('7', byteToHexChar((byte) 7));
        assertEquals('8', byteToHexChar((byte) 8));
        assertEquals('9', byteToHexChar((byte) 9));
        assertEquals('A', byteToHexChar((byte) 10));
        assertEquals('B', byteToHexChar((byte) 11));
        assertEquals('C', byteToHexChar((byte) 12));
        assertEquals('D', byteToHexChar((byte) 13));
        assertEquals('E', byteToHexChar((byte) 14));
        assertEquals('F', byteToHexChar((byte) 15));
    }

    @Test
    public void testBytesToHex() {
        byte[] bytes = { (byte) 0xA9, (byte) 0xED, (byte) 0x67, (byte) 0x2B, (byte) 0x2A, (byte) 0xDB, (byte) 0xC1, (byte) 0x8A, (byte) 0xF5, (byte) 0xE7, (byte) 0x40, (byte) 0x97, (byte) 0x4F, (byte) 0x0F, (byte) 0x4B, (byte) 0xE9, (byte) 0x43, (byte) 0xCF, (byte) 0xA8, (byte) 0xC7 };
        assertEquals("A9ED672B2ADBC18AF5E740974F0F4BE943CFA8C7", bytesToHex(bytes));
    }

    @Test
    public void testSHA1() {
        String userId = "MIKE";
        String password = "mike11";
        String expectedSha1 = "A9ED672B2ADBC18AF5E740974F0F4BE943CFA8C7";
        //System.out.println("User ID: \"" + userId + "\"");
        //System.out.println("Password: \"" + password + "\"");
        //System.out.println("Expected SHA-1: \"" + expectedSha1 + "\"");
        String calculatedSha1 = "null";
        try {
            calculatedSha1 = SHA1(userId + password);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException");
            e.printStackTrace();
        }
        //System.out.println("Calculated SHA-1: \"" + calculatedSha1 + "\"");
        assertEquals(expectedSha1, calculatedSha1);
    }
}