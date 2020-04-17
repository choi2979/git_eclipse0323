package solo;

import java.util.List;

import design.book.BookVO;

public class BookController {
	BookApp bookApp = null;
	public BookController(BookApp bookApp) {
		this.bookApp = bookApp;
	}

	public List<BookVO> sendALL(BookVO pbVO) {
		List<BookVO> bList = null;
		String command = pbVO.getCommand();
		if("all".equals(command)) {
			bList = bookApp.bDao.bookList(pbVO);
		}
		return bList;
	}

	public BookVO send(BookVO pbVO) {
		BookVO rbVO = new BookVO();
		String command = pbVO.command;
		if("detail".equals(command)) {
			rbVO = bookApp.bDao.bookDetail(pbVO);
		}
		else if("delete".equals(command)) {
			int result = 0;
			result = bookApp.bDao.bookDelete(pbVO);
			rbVO.setResult(result);
		}
		return rbVO;
	}
}
