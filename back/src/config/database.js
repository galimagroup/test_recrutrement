const mongoose = require('mongoose');

const connectDB = async () => {
  try {
    const conn = await mongoose.connect(process.env.MONGODB_URI, {
      // Options recommandées pour MongoDB
      useNewUrlParser: true,
      useUnifiedTopology: true,
    });

    console.log(`Connexion a MongoDb reussie: ${conn.connection.host}`);

    // Gestion des erreurs après la connexion initiale
    mongoose.connection.on('erreur', (err) => {
      console.error(`Erreur de connexion: ${err}`);
    });

    mongoose.connection.on('deconnecté', () => {
      console.log('Deconnexion de MongoDB');
    });

    // Gestion propre de la déconnexion lors de l'arrêt de l'application
    process.on('SIGINT', async () => {
      await mongoose.connection.close();
      process.exit(0);
    });

  } catch (error) {
    console.error(`Erreur: ${error.message}`);
    process.exit(1);
  }
};

module.exports = connectDB;