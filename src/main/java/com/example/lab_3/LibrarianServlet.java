package com.example.lab_3;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.json.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "LibrarianServlet", value = "/LibrarianServlet")
public class LibrarianServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // Use the servlet context's real path to get the correct file path
        String filePath = getServletContext().getRealPath("/WEB-INF/data/books.json");

        // Check if the file exists
        File file = new File(filePath);
        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("File not found");
            return;
        }

        // Read the file content
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            // Set the response content type and write the file content
            response.setContentType("application/json");
            response.getWriter().write(content.toString());
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error reading file: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = Logger.getLogger(LibrarianServlet.class.getName());
        logger.setLevel(Level.FINER);

        try (BufferedReader requestReader = request.getReader()) {
            StringBuilder requestJsonContent = new StringBuilder();
            String requestLine;
            while ((requestLine = requestReader.readLine()) != null) {
                requestJsonContent.append(requestLine);
            }
            logger.finer("Received request: " + requestJsonContent.toString());

            JSONObject requestJson = new JSONObject(requestJsonContent.toString());
            String bookName = requestJson.getString("title");
            String authorName = requestJson.getString("author");
            String coverLink = requestJson.getString("coverLink");
            String year = requestJson.getString("year");
            String ISBN = requestJson.getString("ISBN");

            // Use the servlet context's real path to get the correct file path
            String filePath = getServletContext().getRealPath("/WEB-INF/data/books.json");

            try (FileReader reader = new FileReader(filePath);
                 BufferedReader jsonReader = new BufferedReader(reader)) {
                StringBuilder jsonContent = new StringBuilder();
                String line;
                while ((line = jsonReader.readLine()) != null) {
                    jsonContent.append(line);
                }

                JSONObject booksJson = new JSONObject(jsonContent.toString());
                JSONArray booksArray = booksJson.getJSONArray("books");

                JSONObject newBook = new JSONObject();
                newBook.put("title", bookName);
                newBook.put("author", authorName);
                newBook.put("coverLink", coverLink);
                newBook.put("year", year);
                newBook.put("ISBN", ISBN);
                booksArray.put(newBook);

                try (FileWriter fileWriter = new FileWriter(filePath)) {
                    fileWriter.write(booksJson.toString());
                    fileWriter.flush();
                    logger.info("Updated books.json: " + booksJson.toString());
                } catch (IOException e) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    logger.severe("Error while writing to books.json: " + e.getMessage());
                    e.printStackTrace();
                }

                response.setStatus(HttpServletResponse.SC_CREATED);
                logger.info("Created new book: " + newBook.toString());
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                logger.severe("Error while processing request: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
