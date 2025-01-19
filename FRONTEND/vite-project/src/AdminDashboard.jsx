import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom"; 
import "./styles/AdminDashboard.css";

const AdminDashboard = () => {
    const [view, setView] = useState("products"); // Default view is products
    const [products, setProducts] = useState([]);
    const [users, setUsers] = useState([]);
    const [newProduct, setNewProduct] = useState({ 
        category: "", 
        name: "", 
        price: "", 
        description: "", 
        image: ""  // ✅ Add Image Field
    });
    const [editProduct, setEditProduct] = useState(null);
    const navigate = useNavigate();

    // ✅ Sign Out Function
    const handleSignOut = async () => {
        try {
            console.log("Signing out...");
    
            const response = await fetch("http://localhost:8080/logout", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json", // ✅ Ensure content type is allowed in backend
                },
            });
    
            if (response.ok) {
                console.log("Signed out successfully!");
    
                // ✅ Clear session data
                localStorage.removeItem("adminToken");
                localStorage.removeItem("user");
                sessionStorage.clear();
    
                // ✅ Dispatch storage event so Navbar updates immediately
                window.dispatchEvent(new Event("storage"));
    
                // ✅ Redirect to landing page
                navigate("/");
    
                // ✅ Reload the page after a short delay
                setTimeout(() => window.location.reload(), 100);
            } else {
                console.error("Failed to sign out:", await response.text());
                alert("Failed to sign out.");
            }
        } catch (error) {
            console.error("Error signing out:", error);
        }
    };
    
    useEffect(() => {
        fetchProducts();
    }, []);

    // ✅ Fetch Products
    const fetchProducts = async () => {
        try {
            const response = await fetch("http://localhost:8080/admin/viewProducts", {
                headers: { 
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + localStorage.getItem("adminToken") // ✅ Add this
                }
            });
    
            if (response.status === 403) {
                alert("Access denied. Please log in as Admin.");
                navigate("/login"); // Redirect to login page if not authorized
                return;
            }
    
            const data = await response.json();
            console.log("Fetched Products:", data);
    
            if (Array.isArray(data)) {
                setProducts(data);
            } else {
                console.error("Unexpected response format:", data);
                setProducts([]);
            }
        } catch (error) {
            console.error("Error fetching products:", error);
        }
    };
    
    const fetchUsers = async () => {
        try {
            const response = await fetch("http://localhost:8080/admin/viewUsers", {
                headers: { 
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + localStorage.getItem("adminToken") // ✅ Add this
                }
            });
    
            if (response.status === 403) {
                alert("Access denied. Please log in as Admin.");
                navigate("/login");
                return;
            }
    
            const data = await response.json();
            setUsers(data);
        } catch (error) {
            console.error("Error fetching users:", error);
        }
    };
    

    // ✅ Delete Product
    const handleDeleteProduct = async (productName) => {
        try {
            const response = await fetch("http://localhost:8080/admin/deleteProduct", {
                method: "DELETE",
                headers: { "Content-Type": "text/plain" },
                body: productName,
            });

            if (response.ok) {
                alert("Product deleted successfully!");
                fetchProducts(); // Refresh product list
            } else {
                alert("Failed to delete product.");
            }
        } catch (error) {
            console.error("Error deleting product:", error);
        }
    };

    // ✅ Add Product
    const handleFileChange = (event) => {
        setNewProduct({ ...newProduct, imageFile: event.target.files[0] });
    };
    
    const handleAddProduct = async () => {
        const formData = new FormData();
        formData.append("category", newProduct.category);
        formData.append("name", newProduct.name);
        formData.append("price", newProduct.price);
        formData.append("description", newProduct.description);
        formData.append("image", newProduct.image); // ✅ Attach image file
    
        try {
            const response = await fetch("http://localhost:8080/admin/uploadProduct", {
                method: "POST",
                body: formData, // ✅ Send as multipart form data
            });
    
            if (response.ok) {
                alert("Product added successfully!");
                setNewProduct({ category: "", name: "", price: "", description: "", image: null });
                fetchProducts(); // Refresh product list
            } else {
                alert("Failed to add product.");
            }
        } catch (error) {
            console.error("Error adding product:", error);
        }
    };    
    
    // ✅ Add file input field in the form
    <input type="file" accept="image/*" onChange={handleFileChange} />

    // ✅ Update Product
    const handleUpdateProduct = async () => {
        if (!editProduct) return;

        const updateData = `${editProduct.oldName},${editProduct.category},${editProduct.name},${editProduct.price},${editProduct.description}`;

        try {
            const response = await fetch("http://localhost:8080/admin/updateProduct", {
                method: "POST",
                headers: { "Content-Type": "text/plain" },
                body: updateData,
            });

            if (response.ok) {
                alert("Product updated successfully!");
                setEditProduct(null);
                fetchProducts(); // Refresh product list
            } else {
                alert("Failed to update product.");
            }
        } catch (error) {
            console.error("Error updating product:", error);
        }
    };

    // ✅ Delete User
    const handleDeleteUser = async (username) => {
        try {
            const response = await fetch("http://localhost:8080/admin/deleteUser", {
                method: "DELETE",
                headers: { "Content-Type": "text/plain" },
                body: username,
            });

            if (response.ok) {
                alert("User deleted successfully!");
                fetchUsers(); // Refresh user list
            } else {
                alert("Failed to delete user.");
            }
        } catch (error) {
            console.error("Error deleting user:", error);
        }
    };

    return (
        <div className="admin-container">
            <h2>🛒 Admin Dashboard</h2>

            {/* Admin Navigation Buttons */}
            <div className="admin-options">
                <button className={view === "products" ? "active" : ""} onClick={() => setView("products")}>
                    📦 Manage Products
                </button>
                <button className={view === "users" ? "active" : ""} onClick={() => { setView("users"); fetchUsers(); }}>
                    👤 View Users
                </button>
                <button className="signout-btn" onClick={handleSignOut}>🚪 Sign Out</button>
            </div>

            {/* ✅ Product Management View */}
            {view === "products" && (
                <div className="table-container">
                    <h3>📋 Product List</h3>
                    <table className="admin-table">
                        <thead>
                            <tr>
                                <th>Category</th>
                                <th>Name</th>
                                <th>Price (RM)</th>
                                <th>Description</th>
                                <th>Image</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {products.map((product, index) => (
                                <tr key={index}>
                                    <td>{product.category}</td>
                                    <td>{product.name}</td>
                                    <td>RM {product.price}</td>
                                    <td>{product.description}</td>
                                    <td>
                                        <img className="product-image" 
                                            src={`http://localhost:8080/${product.image}`} 
                                            alt={product.name} 
                                            onError={(e) => e.target.style.display = 'none'} 
                                        />
                                    </td>
                                    <td className="action-buttons">
                                        <button className="edit-btn" onClick={() => setEditProduct({ ...product, oldName: product.name })}>
                                            ✏ Edit
                                        </button>
                                        <button className="delete-btn" onClick={() => handleDeleteProduct(product.name)}>
                                            🗑 Delete
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>

                    {/* ✅ Add Product */}
                    <h3>➕ Add Product</h3>
                    <div className="form-container">
                        <input type="text" placeholder="Category" value={newProduct.category} 
                            onChange={(e) => setNewProduct({ ...newProduct, category: e.target.value })} 
                        />
                        <input type="text" placeholder="Name" value={newProduct.name} 
                            onChange={(e) => setNewProduct({ ...newProduct, name: e.target.value })} 
                        />
                        <input type="number" placeholder="Price" value={newProduct.price} 
                            onChange={(e) => setNewProduct({ ...newProduct, price: e.target.value })} 
                        />
                        <input type="text" placeholder="Description" value={newProduct.description} 
                            onChange={(e) => setNewProduct({ ...newProduct, description: e.target.value })} 
                        />
                        <input type="file" accept="image/*" onChange={(e) => setNewProduct({ ...newProduct, image: e.target.files[0] })} />

                        <button className="add-btn" onClick={handleAddProduct}>➕ Add Product</button>
                    </div>

                    {/* ✅ Edit Product */}
                    {editProduct && (
                        <div className="form-container">
                            <h3>✏ Edit Product</h3>
                            <input type="text" placeholder="Category" value={editProduct.category} onChange={(e) => setEditProduct({ ...editProduct, category: e.target.value })} />
                            <input type="text" placeholder="New Name" value={editProduct.name} onChange={(e) => setEditProduct({ ...editProduct, name: e.target.value })} />
                            <input type="number" placeholder="New Price" value={editProduct.price} onChange={(e) => setEditProduct({ ...editProduct, price: e.target.value })} />
                            <input type="text" placeholder="New Description" value={editProduct.description} onChange={(e) => setEditProduct({ ...editProduct, description: e.target.value })} />
                            <button className="update-btn" onClick={handleUpdateProduct}>✅ Update Product</button>
                            <button className="cancel-btn" onClick={() => setEditProduct(null)}>❌ Cancel</button>
                        </div>
                    )}
                </div>
            )}

            {/* ✅ User Management View */}
            {view === "users" && (
                <div className="table-container">
                    <h3>👥 User List</h3>
                    <table className="admin-table">
                        <thead>
                            <tr>
                                <th>Username</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {users.map((user, index) => (
                                <tr key={index}>
                                    <td>{user.username}</td>
                                    <td className="action-buttons">
                                        <button className="delete-btn" onClick={() => handleDeleteUser(user.username)}>
                                            🗑 Delete
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>

    );
};

export default AdminDashboard;
