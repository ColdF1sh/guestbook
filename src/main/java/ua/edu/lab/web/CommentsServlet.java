package ua.edu.lab.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.lab.dao.CommentDao;
import ua.edu.lab.model.CreateCommentRequest;

@WebServlet("/comments")
public class CommentsServlet extends HttpServlet {

    private CommentDao dao;
    private final ObjectMapper mapper = new ObjectMapper();
    private static final Logger log =
            LoggerFactory.getLogger(CommentsServlet.class);

    @Override
    public void init() {
        try {
            dao = new CommentDao();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.setContentType("application/json;charset=UTF-8");
            mapper.writeValue(resp.getOutputStream(), dao.findAllDesc());
        } catch (Exception e) {
            resp.setStatus(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            CreateCommentRequest r =
                    mapper.readValue(req.getInputStream(), CreateCommentRequest.class);

            if (r.author == null || r.author.isBlank() || r.author.length() > 64 ||
                    r.text == null || r.text.isBlank() || r.text.length() > 1000) {
                resp.setStatus(400);
                return;
            }

            long id = dao.insert(r.author, r.text);

            log.info("New comment: author={}, textLength={}, id={}",
                    r.author, r.text.length(), id);

            resp.setStatus(204);
        } catch (Exception e) {
            resp.setStatus(500);
        }
    }
}
