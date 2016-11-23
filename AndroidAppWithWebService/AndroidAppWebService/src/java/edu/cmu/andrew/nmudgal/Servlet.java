/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cmu.andrew.nmudgal;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which takes the get request from adnroid app and forms a request to be sent to REST api of Merriam Webster and
 * gets the response, parses it using Model class methods and sends the response back to android app
 * @author Nitish
 */
@WebServlet(name = "Servlet",
        urlPatterns = {"/servlet"})
public class Servlet extends HttpServlet {

    Model thesaurusModel = null; //Creation of model object reference    

    @Override
    public void init() {
        thesaurusModel = new Model(); // Initializing model object
    }
    
    // https://warm-chamber-91456.herokuapp.com/ | https://git.heroku.com/warm-chamber-91456.git
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // storing the requested string from android app
        String searchString = request.getParameter("searchstring");
        if(searchString == null){
            searchString = "";
        }        
        Synonym synonyms = null;
        synonyms = thesaurusModel.modifyResponse(searchString);
        //System.out.println(parsedResponse);
        response.setStatus(200);
        // tell the client the type of the response
        response.setContentType("text/xml");
        // return the value from a GET request
        PrintWriter out = response.getWriter();
        out.write(thesaurusModel.toXML(synonyms));
        out.flush();
        return;        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
