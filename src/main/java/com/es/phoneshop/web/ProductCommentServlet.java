package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.RateCalculationException;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.service.CommentService;
import com.es.phoneshop.service.DefaultCommentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductCommentServlet extends HttpServlet {

    private CommentService commentService;

    @Override
    public void init() throws ServletException {
        commentService = DefaultCommentService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int rate = Integer.parseInt(request.getParameter("rate"));
        String name = request.getParameter("name");
        String comment = request.getParameter("comment");
        Long productId = Long.valueOf(request.getParameter("productId"));
        try {
            commentService.addComment(productId, rate, name, comment);
            request.setAttribute("name", name);
            request.setAttribute("comment", comment);
            request.setAttribute("rate", rate);
            request.getRequestDispatcher("/WEB-INF/pages/comment.jsp").forward(request, response);
        } catch (ProductNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (RateCalculationException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        }
    }
}
