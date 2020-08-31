package jums;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hayashi-s
 */
public class UpdateResult extends HttpServlet {

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateResult() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("アップデートリザルト開始");

		try {
			request.setCharacterEncoding("UTF-8");
			HttpSession session = request.getSession();

			//フォームからの入力を取得、javabeansに取得
			UserDataBeans udb = new UserDataBeans();

            udb.setName(request.getParameter("name"));
            udb.setYear(request.getParameter("year"));
            udb.setMonth(request.getParameter("month"));
            udb.setDay(request.getParameter("day"));
            udb.setType(request.getParameter("type"));
            udb.setTell(request.getParameter("tel"));
            udb.setComment(request.getParameter("comment"));

            //DTOオブジェクトにマッピング。DB専用のパラメータに変換
            UserDataDTO userdata = new UserDataDTO();
            udb.UD2DTOMapping(userdata);

            UserDataDAO .getInstance();
            UserDataDAO.update(userdata);

            //結果画面での表示用に入力パラメータ―をリクエストパラメータとして保持
            session.setAttribute("udb", udb);

            request.getRequestDispatcher("/updateresult.jsp").forward(request, response);
            System.out.println("アップデートリザルト終了");
		}catch(Exception e){
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
