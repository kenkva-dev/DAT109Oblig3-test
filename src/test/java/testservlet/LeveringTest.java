package testservlet;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import no.hvl.dat109.Servlet.Levering;
import no.hvl.dat109.hjelpeklasser.Melding;
import no.hvl.dat109.hjelpeklasser.Meldingstype;

public class LeveringTest {
	
	 @Mock
	 HttpServletRequest request;
	 @Mock
	 HttpServletResponse response;

	 Levering servlet;
	 Gson gson;
	 
    @Before
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
    	gson = new GsonBuilder()
		        .excludeFieldsWithoutExposeAnnotation()
		        .create();
    	servlet = new Levering();
    }

    @Test
    public void hentFeilAvfallsplassGirFeil() throws ServletException, IOException {
        when(request.getParameter("telefonnr")).thenReturn("81549300");
        when(request.getParameter("avfallsplassID")).thenReturn("sa6");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);
        String result = sw.getBuffer().toString();
        String expected = gson.toJson(new Melding(Meldingstype.FEIL)).toString();
        assertTrue(result.contains(expected));
    }
    
    @Test
    public void hentGyldigAvfallsplassGirOk() throws ServletException, IOException {
        when(request.getParameter("telefonnr")).thenReturn("81549300");
        when(request.getParameter("avfallsplassID")).thenReturn("10");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);
        String result = sw.getBuffer().toString();
        String expected = gson.toJson(new Melding(Meldingstype.ProduktForLeveringOK)).toString();
        assertTrue(result.contains(expected));
    }
    
    @Test
    public void sendFeilLeveringsoversiktGirFeil() throws ServletException, IOException {
        when(request.getParameterValues("leveringsoversiktId")).thenReturn(new String[]{"-1"});

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);
        String result = sw.getBuffer().toString();
        String expected = gson.toJson(new Melding(Meldingstype.IngenProdukt)).toString();
        assertTrue(result.contains(expected));
    }
    
    @Test
    public void sendGyldigLeveringsoversiktGirOk() throws ServletException, IOException {
    	when(request.getParameterValues("leveringsoversiktId")).thenReturn(new String[]{"9", "10"});

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);
        String result = sw.getBuffer().toString();
        String expected = gson.toJson(new Melding(Meldingstype.ProduktLevert)).toString();
        assertTrue(result.contains(expected));
    }
    
    


}