const express = require('express');
const router = express.Router();
const authController = require('../controllers/authController');

router.post('/account', authController.createAccount);
router.post('/token', authController.login);

module.exports = router;
