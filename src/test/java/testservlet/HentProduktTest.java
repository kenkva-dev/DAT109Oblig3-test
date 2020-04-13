package testservlet;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import no.hvl.dat109.Servlet.HentProdukt;
import no.hvl.dat109.hjelpeklasser.Melding;
import no.hvl.dat109.hjelpeklasser.Meldingstype;

public class HentProduktTest {
	
	 @Mock
	 HttpServletRequest request;
	 @Mock
	 HttpServletResponse response;

	 HentProdukt servlet;
	 Gson gson;
	 
    @Before
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
    	gson = new GsonBuilder()
		        .excludeFieldsWithoutExposeAnnotation()
		        .create();
    	servlet = new HentProdukt();
    }

    @Test
    public void hentFeilProduktGirFeil() throws ServletException, IOException {

        when(request.getParameter("barcode")).thenReturn("asd2241421ads");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);
        String result = sw.getBuffer().toString();
        String expected = gson.toJson(new Melding(Meldingstype.ProduktFinnastIkkje)).toString();
        assertTrue(result.contains(expected));
    }
    
    @Test
    public void hentKorrektProduktGirOk() throws ServletException, IOException {

        when(request.getParameter("barcode")).thenReturn("7037710037173");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);
        String result = sw.getBuffer().toString();
        String expectedProdukt = "7037710037173"; 
        String expectedMsg = gson.toJson(new Melding(Meldingstype.ProduktOK)).toString();
        assertTrue(result.contains(expectedProdukt));
        assertTrue(result.contains(expectedMsg));
    }
}