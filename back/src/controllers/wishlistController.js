const Wishlist = require('../models/Wishlist');

exports.getWishlist = async (req, res) => {
  try {
    let wishlist = await Wishlist.findOne({ userId: req.user.userId }).populate('products');
    if (!wishlist) {
      wishlist = await Wishlist.create({ userId: req.user.userId, products: [] });
    }
    res.json(wishlist);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

exports.addToWishlist = async (req, res) => {
  try {
    const { productId } = req.body;
    let wishlist = await Wishlist.findOne({ userId: req.user.userId });
    if (!wishlist) {
      wishlist = await Wishlist.create({ userId: req.user.userId, products: [] });
    }
    
    if (!wishlist.products.includes(productId)) {
      wishlist.products.push(productId);
      await wishlist.save();
    }
    
    res.json(wishlist);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};