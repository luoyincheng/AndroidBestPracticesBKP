package yincheng.tinytank.ui.activity;

import java.util.List;

import yincheng.tinytank.common.GenericItemHolder;
import yincheng.tinytank.ui.activity.base.GenericActivityWithRecyclerView;

import static yincheng.tinytank.provider.ItemHolderProvider.AnimationList;

/**
 * Mail   : luoyincheng@gmail.com
 * Date   : 2018:04:24 21:04
 * Github : yincheng.luo
 */
public class AnimationActivity extends GenericActivityWithRecyclerView {
	@Override
	protected List<GenericItemHolder> getGenericItemHolders() {
		return AnimationList;
	}
}
