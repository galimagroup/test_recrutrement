const express = require('express');
const cors = require('cors');
const mongoose = require('mongoose');
require('dotenv').config();

const authRoutes = require('./src/routes/authRoutes');
const productRoutes = require('./src/routes/productRoutes');
const cartRoutes = require('./src/routes/cartRoutes');
const wishlistRoutes = require('./src/routes/wishlistRoutes');
const connectDB = require('./src/config/database');

// Connexion à la base de données
connectDB();

const app = express();

app.use(cors());
app.use(express.json());

mongoose.connect(process.env.MONGODB_URI)
  .then(() => console.log('Connexion a MongoDB'))
  .catch(err => console.error('Erreur de connexion MonGODB:', err));

app.use('/api', authRoutes);
app.use('/api/products', productRoutes);
app.use('/api/cart', cartRoutes);
app.use('/api/wishlist', wishlistRoutes);

const PORT = process.env.PORT || 3000;

app.listen(PORT, () => {
  console.log(`Demarrage du Serveur ${PORT}`);
});