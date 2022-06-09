package org.example;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cs")
public class CalcServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
    throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<h1>Calc Servlet</h1>");

        int num, cumulativeSum = 0;
        String data = req.getParameter("num");
        
        if( data == null || data.trim().length() == 0)
            num = 0;
        else
            num = Integer.parseInt(data);
               
        boolean found = false;
        for(Cookie cookie: req.getCookies()){
            if( cookie.getName().equals("bbc")){
                System.out.print("cookie found...  ");
                cumulativeSum = Integer.parseInt(cookie.getValue());
                cumulativeSum += num;
                cookie.setValue(Integer.toString(cumulativeSum));
                res.addCookie(cookie);
                found = true;   
                break;             
            }
        }
        if( !found) {
            System.out.println("new cookie created...");
            Cookie cookie = new Cookie("bbc", Integer.toString(num));
            cookie.setMaxAge(60*60); //1 hrs
            res.addCookie(cookie);            
        }
        out.println("<h1>Result: " + cumulativeSum + "</h1>");

        out.println("<form>");
        out.println("Enter a number: <input name='num'/>");
        out.println("<input type='submit'/>");
        out.println("</form>");  
    }  
}
