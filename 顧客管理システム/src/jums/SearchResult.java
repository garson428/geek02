package jums;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hayashi-s
 */
public class SearchResult extends HttpServlet {

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchResult() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("サーチリザルト開始");
		//セッション開始
		HttpSession session = request.getSession();

		try{
            request.setCharacterEncoding("UTF-8");//リクエストパラメータの文字コードをUTF-8に変更

            //フォームからの入力を取得して、JavaBeansに格納
            UserDataBeans udb = new UserDataBeans();
            udb.setName(request.getParameter("name"));
            udb.setYear(request.getParameter("year"));
            udb.setType(request.getParameter("type"));

            session.setAttribute("udb", udb);


            //DTOオブジェクトにマッピング。DB専用のパラメータに変換
            UserDataDTO searchData = new UserDataDTO();
            udb.UD2DTOMapping(searchData);

            //UserDataDTOからList<UserDataDTO>に変更・・・1
            List<UserDataDTO> resultData = UserDataDAO .getInstance().search(searchData);

            //DAOで取得した値をリクエストスコープに格納
            request.setAttribute("resultData", resultData);
            session.setAttribute("resultData", resultData);


            request.getRequestDispatcher("/searchresult.jsp").forward(request, response);
            System.out.println("サーチリザルト終了");
        }catch(Exception e){
            //何らかの理由で失敗したらエラーページにエラー文を渡して表示。想定は不正なアクセスとDBエラー
            request.setAttribute("error", e.getMessage());
            e.printStackTrace();
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
