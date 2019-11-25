package yincheng.tinytank.ui.activity;

import java.util.List;

import yincheng.tinytank.common.GenericItemHolder;
import yincheng.tinytank.ui.activity.base.GenericActivityWithRecyclerView;

import static yincheng.tinytank.provider.ItemHolderProvider.ArchitectureList;

/**
 * Mail : luoyincheng@gmail.com
 * Date   : 2018:04:01 17:37
 * Github : yincheng.luo
 */

public class ArchitectureActivity extends GenericActivityWithRecyclerView {


	@Override
	protected List<GenericItemHolder> getGenericItemHolders() {
		return ArchitectureList;
	}
}
