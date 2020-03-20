package board.controller;

import board.dao.PostDao;
import board.dto.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/*")
public class FrontController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
        String viewPath = "";

        if (path.equals("/")) viewPath = "main";
        else if (path.startsWith("/post")) viewPath = new PostController().execute(request, response);
        else if (path.startsWith("/comment")) viewPath = new CommentController().execute(request, response);
        else if (path.startsWith("/member")) viewPath = new MemberController().execute(request, response);


        if (viewPath.equals("")) {
            request.getRequestDispatcher("/WEB-INF/error/404.jsp").forward(request, response);
            return;
        }
        if (viewPath.startsWith("redirect:/")) {
            response.sendRedirect(viewPath);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/"+ viewPath + ".jsp").include(request, response);
    }
}
