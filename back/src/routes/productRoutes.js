const express = require('express');
const router = express.Router();
const productController = require('../controllers/productController');
const auth = require('../middlewares/auth');
const isAdmin = require('../middlewares/admin');

router.get('/', auth, productController.getProducts);
router.get('/:id', auth, productController.getProduct);
router.post('/', [auth, isAdmin], productController.createProduct);
router.put('/:id', [auth, isAdmin], productController.updateProduct);
router.delete('/:id', [auth, isAdmin], productController.deleteProduct);

module.exports = router;