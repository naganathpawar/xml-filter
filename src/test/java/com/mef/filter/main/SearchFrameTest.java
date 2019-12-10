package com.mef.filter.main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SearchFrameTest{

    @Test
    void SearchFrameTest ( ) {
        try {
            SearchFrame searchFrame = new SearchFrame ( );
            searchFrame.generateJTable ( );
        }catch ( Exception ex ){
            Assertions.assertAll ("NULL ERROR: "+ ex.getMessage () );
        }
    }
}