const mongoose = require('mongoose');

const productSchema = new mongoose.Schema({
  code: { type: String, required: true, unique: true },
  name: { type: String, required: true },
  description: String,
  image: String,
  category: String,
  price: { type: Number, required: true },
  quantity: { type: Number, required: true },
  internalReference: String,
  shellId: Number,
  inventoryStatus: {
    type: String,
    enum: ['INSTOCK', 'LOWSTOCK', 'OUTOFSTOCK'],
    default: 'INSTOCK'
  },
  rating: { type: Number, default: 0 },
}, { timestamps: true });

module.exports = mongoose.model('Product', productSchema);
