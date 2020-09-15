package yincheng.tinytank.view.widget;

/**
 * Mail   : luoyincheng@gmail.com
 * Date   : 2018:04:27 8:24
 * Github : yincheng.luo
 */
public class SwipeLayoutManager {

	/**
	 * 记录当前打开的SwipeLayout
	 */
	private SwipeLayout openInstance;

	private SwipeLayoutManager() {
	}

	public static SwipeLayoutManager getInstance() {
		return LazyHolder.INSTANCE;
	}

	/**
	 * 设置当前打开的SwipeLayout
	 */
	public void setOpenInstance(SwipeLayout swipeLayout) {
		openInstance = swipeLayout;
	}

	/**
	 * 判断一个条目能否侧滑
	 */
	public boolean isCouldSwipe(SwipeLayout swipeLayout) {

		// 已经打开。可以侧滑
		if (isOpenInstance(swipeLayout)) {
			return true;
		}

		// 都没有打开也可以侧滑
		return openInstance == null;
	}

	/**
	 * 判断是不是打开的条目
	 */
	public boolean isOpenInstance(SwipeLayout swipeLayout) {

		return swipeLayout == openInstance;
	}

	/**
	 * 关闭打开的条目
	 */
	public void closeOpenInstance() {
		if (openInstance != null) {
			openInstance.closeDeleteMenu();
			openInstance = null;
		}
	}

	// 静态内部类模式单例
	private static class LazyHolder {
		private static final SwipeLayoutManager INSTANCE = new SwipeLayoutManager();
	}

}
