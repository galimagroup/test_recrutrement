const express = require('express');
const router = express.Router();
const wishlistController = require('../controllers/wishlistController');
const auth = require('../middleware/auth');

router.get('/', auth, wishlistController.getWishlist);
router.post('/add', auth, wishlistController.addToWishlist);

module.exports = router;