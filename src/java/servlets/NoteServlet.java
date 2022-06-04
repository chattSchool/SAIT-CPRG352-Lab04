package servlets;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Note;

/**
 *
 * @author Dakota Chatt
 */
public class NoteServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String filePath = getServletContext().getRealPath("/WEB-INF/note.txt");
        
        BufferedReader in = new BufferedReader(new FileReader(new File(filePath)));
        
        String noteTitle = in.readLine();
        
        String noteContents = in.readLine();
        String nextLine = in.readLine();

        while(nextLine != null) {
            noteContents += nextLine;
            
            nextLine = in.readLine();
        }
        
        in.close();
        
        Note note = new Note(noteTitle, noteContents);
        
        request.setAttribute("note", note);
        
        String edit = request.getParameter("edit");
        
        if(edit != null) {
            getServletContext().getRequestDispatcher("/WEB-INF/editnote.jsp").forward(request, response);
            return;
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/viewnote.jsp").forward(request, response);
            return;   
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String filePath = getServletContext().getRealPath("/WEB-INF/note.txt");
        
        String editedTitle = request.getParameter("title");
        String editedContents = request.getParameter("contents");
        
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath, false))); 
        
        out.println(editedTitle);
        out.println(editedContents);
        
        out.close();
        
        BufferedReader in = new BufferedReader(new FileReader(new File(filePath)));        
        
        String noteTitle = in.readLine();
        String noteContents = in.readLine();
        String nextLine = in.readLine();

        while(nextLine != null) {
            noteContents += nextLine;
            
            nextLine = in.readLine();
        }
        
        in.close();
        
        Note note = new Note(noteTitle, noteContents);
        request.setAttribute("note", note);
        
        getServletContext().getRequestDispatcher("/WEB-INF/viewnote.jsp").forward(request, response);
        return;   
    }
}
