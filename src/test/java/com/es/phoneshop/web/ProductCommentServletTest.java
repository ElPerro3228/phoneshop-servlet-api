package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.RateCalculationException;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.service.CommentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductCommentServletTest {

    private final static String NAME = "name";
    private final static String COMMENT = "comment";
    private final static String RATE = "5";
    private final static String PRODUCT_ID = "1";
    private final static String PATH = "/WEB-INF/pages/comment.jsp";

    @Mock
    private PrintWriter writer;
    @Mock
    private CommentService commentService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @InjectMocks
    private ProductCommentServlet servlet;
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;
    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    @Before
    public void setup(){
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getParameter(eq("rate"))).thenReturn(RATE);
        when(request.getParameter(eq("name"))).thenReturn(NAME);
        when(request.getParameter(eq("comment"))).thenReturn(COMMENT);
        when(request.getParameter(eq("productId"))).thenReturn(PRODUCT_ID);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        doNothing().when(commentService).addComment(anyLong(), anyInt(), anyString(), anyString());

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("name"), stringArgumentCaptor.capture());
        verify(request).setAttribute(eq("comment"), stringArgumentCaptor.capture());
        verify(request).setAttribute(eq("rate"), integerArgumentCaptor.capture());
        verify(request).getRequestDispatcher(stringArgumentCaptor.capture());

        List<String> expectedValues = new ArrayList<>();
        expectedValues.add(NAME);
        expectedValues.add(COMMENT);
        expectedValues.add(PATH);

        assertEquals(expectedValues, stringArgumentCaptor.getAllValues());
        assertEquals(5, (int) integerArgumentCaptor.getValue());
    }

    @Test
    public void shouldCatchProductNotFoundException() throws IOException, ServletException {
        doThrow(new ProductNotFoundException()).when(commentService).addComment(anyLong(), anyInt(), anyString(), anyString());

        servlet.doPost(request, response);

        verify(response).sendError(integerArgumentCaptor.capture());
        assertEquals(HttpServletResponse.SC_NOT_FOUND, (int) integerArgumentCaptor.getValue());
    }

    @Test
    public void shouldCatchRateCalculatingException() throws IOException, ServletException {
        when(response.getWriter()).thenReturn(writer);
        doThrow(new RateCalculationException("Wrong rate was received")).when(commentService).addComment(anyLong(), anyInt(), anyString(), anyString());

        servlet.doPost(request, response);

        verify(response).setStatus(integerArgumentCaptor.capture());
        verify(writer).write(stringArgumentCaptor.capture());

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, (int) integerArgumentCaptor.getValue());
        assertEquals("Wrong rate was received", stringArgumentCaptor.getValue());
    }
}
