import { BrowserRouter as Router, Routes, Route, useLocation } from "react-router-dom";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import AdminDashboard from "./AdminDashboard";
import LandingPage from "./LandingPage";
import About from "./About";
import Farmer from "./Farmer";
import Shop from "./Shop";
import Login from "./Login";
import CartPage from "./CartPage";
import OrderSuccess from "./OrderSuccess"; 
import PurchasesPage from "./PurchasePage"; // ✅ Import Purchases Page

function Layout() {
    const location = useLocation();
    const hideNavFooter = location.pathname === "/admin";  

    return (
        <>
            {!hideNavFooter && <Navbar />}
            <Routes>
                <Route path="/" element={<LandingPage />} />
                <Route path="/about" element={<About />} />
                <Route path="/farmer" element={<Farmer />} />
                <Route path="/shop" element={<Shop />} />
                <Route path="/login" element={<Login />} />
                <Route path="/cart" element={<CartPage />} />
                <Route path="/admin" element={<AdminDashboard />} />
                <Route path="/order-success" element={<OrderSuccess />} />
                <Route path="/purchases" element={<PurchasesPage />} />
                <Route path="*" element={<LandingPage />} />
            </Routes>
            {!hideNavFooter && <Footer />}
        </>
    );
}

function App() {
    return (
        <Router basename="/FreshLokals">
            <Layout />
        </Router>
    );
}

export default App;
