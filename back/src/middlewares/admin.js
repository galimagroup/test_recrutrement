const isAdmin = async (req, res, next) => {
  if (req.user.email !== 'admin@admin.com') {
    return res.status(403).json({ error: 'Access Non autorisé' });
    
  }
  next();
};

module.exports = isAdmin;