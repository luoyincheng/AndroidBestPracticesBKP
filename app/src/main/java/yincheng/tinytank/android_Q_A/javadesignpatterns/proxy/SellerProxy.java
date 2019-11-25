package yincheng.tinytank.android_Q_A.javadesignpatterns.proxy;

public class SellerProxy implements Seller {

	private static final int NUM_SELLER_ACQUIRED = 3;//代理从生产商拿到的货物数量

	private int numOfSoldGoods; // 已经销售的商品数量

	private final Seller mSeller;

	public SellerProxy(Seller seller) {
		this.mSeller = seller;
	}

	@Override
	public void sell(Goods goods) {
		if (numOfSoldGoods < NUM_SELLER_ACQUIRED) {
			mSeller.sell(goods);
			numOfSoldGoods++;
		} else {
			System.out.println("SellerProxy.sell():" + goods + " was sold out");
		}
	}
}