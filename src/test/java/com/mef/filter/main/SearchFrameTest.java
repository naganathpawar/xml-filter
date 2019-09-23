package com.mef.filter.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchFrameTest{

    @Test
    void SearchFrameTest ( ) {
        try {
            SearchFrame searchFrame = new SearchFrame ( );
            searchFrame.generateJTable ( );
        }catch ( Exception ex ){
            assertAll ("NULL ERROR: "+ ex.getMessage () );
        }
    }
}