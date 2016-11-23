package Project1Task4;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nitish
 * BigPictureServlet acts as the controller in MVC implementation for Project1 Task4. It receieves
 * request from prompt.jsp and uses the model to find the bird picture and show it on result.jsp
 * In case of error it redirects to the error.jsp
 */
@WebServlet(name = "BirdPictureServlet",
        urlPatterns = {"/getBirdPicture"})

public class BirdPictureServlet extends HttpServlet {

    BirdPictureModel birdSearchObject = null; //Creation of model object reference

    @Override
    public void init() {
        birdSearchObject = new BirdPictureModel();
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BirdPictureServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BirdPictureServlet at " + request.getParameter("birdname") + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        String nextView;
        String birdName = request.getParameter("birdname");
        
        // determine what type of device our user is
        String ua = request.getHeader("User-Agent");
        boolean mobile; // Mobile device flag
        // prepare the appropriate DOCTYPE for the view pages
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            mobile = true;
            /*
             * This is the latest XHTML Mobile doctype. To see the difference it
             * makes, comment it out so that a default desktop doctype is used
             * and view on an Android or iPhone.
             */
            
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }
        if (birdName != null){
            birdSearchObject.createSearchTag(birdName); // Creates the search tag for the bird url plus fetches the bird url and stores
                                                        //  it in the birdSearchObject
            if(!birdSearchObject.getPictureUrl().equals("")){// If we don't get response transfer control to error page
                request.setAttribute("pictureURL",
                            birdSearchObject.birdPictureSize((mobile) ? "mobile" : "desktop"));
                    // Pass the user search string (pictureTag) also to the view.
                request.setAttribute("pictureTag", birdSearchObject.getPictureTag());
                request.setAttribute("photographerName", birdSearchObject.getPhotographerName());
                nextView = "result.jsp";
            }else{
                nextView = "error.jsp";            
            }            
        }else{//If birdName is null then we have to show the prompt
            nextView = "prompt.jsp";
        }        
        //Transfer control to the next view
        // Transfer control over the the correct "view"
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
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
        processRequest(request, response);
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
