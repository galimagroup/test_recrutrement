import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ShopService } from '../../shop.service';
import { Product } from '../../../../models/product';
import { StorageService } from '../../../../storage.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent implements OnInit {
  productForm: FormGroup;
  token: string = '';
  imageBase64: string = '';
  imagePreview: string | ArrayBuffer | null = null;
  isEditMode: boolean = false;
  productId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private shopService: ShopService,
    private storageService: StorageService,
    private toastr: ToastrService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.productForm = this.fb.group({
      name: ['', Validators.required],
      code: ['', Validators.required],
      category: ['', Validators.required],
      inventoryStatus: ['INSTOCK', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      quantity: ['', [Validators.required, Validators.min(0)]],
      rating: ['', [Validators.min(0), Validators.max(5)]],
      description: ['', Validators.required],
      image: [''],
      internalReference: [''],
      shellId: ['']
    });

    this.token = this.storageService.getItem('token') || '';
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.productId = +params['id'];
        this.loadProductDetails(this.productId);
      }
    });
  }

  loadProductDetails(id: number): void {
    this.shopService.getProductById(id).subscribe({
      next: (product) => {
        this.productForm.patchValue(product);
        if (product.image) {
          this.imagePreview = this.shopService.getImageUrl(product.image);
        }
      },
      error: (error) => {
        console.error('Erreur lors de la récupération du produit :', error);
      }
    });
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
        this.imageBase64 = reader.result?.toString().split(',')[1] || '';
        this.productForm.patchValue({
          image: this.imageBase64
        });
      };
      reader.readAsDataURL(file);
    }
  }

  onSubmit(): void {
    if (this.productForm.valid) {
      const product = new Product(this.productForm.value);

      if (this.isEditMode && this.productId) {
        
        // Modification du produit
        this.shopService.updateProduct(this.productId, product, this.token).subscribe({
          next: () => {
            this.toastr.success('Produit modifié avec succès', 'Succès');
            this.router.navigate(['shop/product-list']);
          },
          error: (error) => {
            this.toastr.error('Erreur lors de la modification du produit', 'Erreur');
          }
        });
      } else {
        // Ajout du produit
        this.shopService.addProduct(product, this.token).subscribe({
          next: () => {
            this.toastr.success('Produit ajouté avec succès', 'Succès');
            this.productForm.reset();
            this.imagePreview = null;
          },
          error: (error) => {
            this.toastr.error('Erreur lors de l\'ajout du produit', 'Erreur');
          }
        });
      }
    } else {
      alert('Veuillez remplir tous les champs obligatoires.');
    }
  }
}
