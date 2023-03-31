package com.egovframework.ple.treeframework.execl;

import java.io.InputStream;

public class ExcelUtilsFactory {


    public static ExcelUtilsBase getInstance(InputStream inputStream) {
        return new ExcelUtilsXlsx(inputStream);
    }

}
