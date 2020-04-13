package testservlet;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;

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

import no.hvl.dat109.Servlet.Login;
import no.hvl.dat109.hjelpeklasser.Melding;
import no.hvl.dat109.hjelpeklasser.Meldingstype;

public class LoginTest {
	
	 @Mock
	 HttpServletRequest request;
	 @Mock
	 HttpServletResponse response;
	 Login servlet;
	 Gson gson;
	 
    @Before
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
    	gson = new GsonBuilder()
		        .excludeFieldsWithoutExposeAnnotation()
		        .create();
    	servlet = new Login();
    }

    @Test
    public void sendKorrektInputGirOk() throws ServletException, IOException {
        when(request.getParameter("telefon")).thenReturn("81549300");
        when(request.getParameter("passord")).thenReturn("12345678");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);
        String result = sw.getBuffer().toString();
        String expected = gson.toJson(new Melding(Meldingstype.LoginOK)).toString();
        assertTrue(result.contains(expected));
    }
    
    @Test
    public void sendFeilPassordGirFeil() throws ServletException, IOException {

        when(request.getParameter("telefon")).thenReturn("81549300");
        when(request.getParameter("passord")).thenReturn("12412412");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);

        String result = sw.getBuffer().toString();
        String expected = gson.toJson(new Melding(Meldingstype.FeilPassord)).toString();
        assertTrue(result.contains(expected));
    }
    
    @Test
    public void feilInputGirFeil() throws ServletException, IOException {

        when(request.getParameter("telefon")).thenReturn("asd13f31ffas");
        when(request.getParameter("passord")).thenReturn("2fafssaff2fa");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);

        String result = sw.getBuffer().toString();
        String expected = gson.toJson(new Melding(Meldingstype.BrukarFinnastIkkje)).toString();
        assertTrue(result.contains(expected));
    }
}