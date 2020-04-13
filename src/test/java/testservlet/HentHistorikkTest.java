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

import no.hvl.dat109.Servlet.HentHistorikk;
import no.hvl.dat109.hjelpeklasser.Melding;
import no.hvl.dat109.hjelpeklasser.Meldingstype;

public class HentHistorikkTest {
	
	 @Mock
	 HttpServletRequest request;
	 @Mock
	 HttpServletResponse response;

	 HentHistorikk servlet;
	 Gson gson;
	 
    @Before
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
    	gson = new GsonBuilder()
		        .excludeFieldsWithoutExposeAnnotation()
		        .create();
    	servlet = new HentHistorikk();
    }

    @Test
    public void hentHistorikkForUkjentBrukarGirTomHistorikk() throws ServletException, IOException {
        when(request.getParameter("telefon")).thenReturn("fish");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);
        String result = sw.getBuffer().toString();
        String expected = gson.toJson(new Melding(Meldingstype.TomHistorikk)).toString();
        assertTrue(result.contains(expected));
    }
    
    @Test
    public void hentHistorForBrukarMedHistorikkGirHistorikk() throws ServletException, IOException {
    	when(request.getParameter("telefon")).thenReturn("81549300");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);
        String result = sw.getBuffer().toString();
        String expected = gson.toJson(new Melding(Meldingstype.HistorikkOK)).toString();
        assertTrue(result.contains(expected));
    }
    

    


}