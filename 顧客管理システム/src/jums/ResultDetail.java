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
public class ResultDetail extends HttpServlet {

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResultDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("リザルトディテイル開始");

		//セッションスタート
		HttpSession session = request.getSession();

		try{
            request.setCharacterEncoding("UTF-8");//リクエストパラメータの文字コードをUTF-8に変更

            //DTOオブジェクトにマッピング。DB専用のパラメータに変換
            UserDataDTO searchData = new UserDataDTO();

            //UserIDが2と固定されているのを修正
            //選択されたレコードのIDをリクエストから取得
            int ID = Integer.parseInt(request.getParameter("id"));
            searchData.setUserID(ID);

            UserDataDTO resultData = UserDataDAO .getInstance().searchByID(searchData);
            session.setAttribute("resultData", resultData);
            //request.setAttribute("resultData", resultData);

            request.getRequestDispatcher("/resultdetail.jsp").forward(request, response);
            System.out.println("リザルトディテイル終了");
        }catch(Exception e){
            //何らかの理由で失敗したらエラーページにエラー文を渡して表示。想定は不正なアクセスとDBエラー
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
