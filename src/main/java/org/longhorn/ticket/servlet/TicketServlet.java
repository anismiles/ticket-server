package org.longhorn.ticket.servlet;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.longhorn.ticket.core.*;

public class TicketServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger( TicketServlet.class );
    private TicketService ticketService;

    public void init(ServletConfig config) throws ServletException {

        super.init(config);
        String zkConn = config.getInitParameter("zkurl");
        logger.info( "zookeeper url: " + zkConn );
        ticketService = new TicketService( zkConn );
        ticketService.start();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        out.println("" + ticketService.nextTicket());
    }

    public void destroy() {
        if ( ticketService != null ) {
            ticketService.stop();
        }
    }
}
