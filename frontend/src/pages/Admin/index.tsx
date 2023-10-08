import { Route, Switch } from "react-router-dom";
import Navbar from "./Navbar";
import './styles.css'
import User from "./User";

const Admin = () => {
  return (
    <div className="admin-container">
      <Navbar />
      <div className="admin-content">
        <Switch>
          <Route path="/admin/products">
            <h1>Product CRUD</h1>
          </Route>
          <Route path="/admin/categories">
            <h1>Product CRUD</h1>
          </Route>
          <Route path="/admin/users">
            <User />
          </Route>
        </Switch>
      </div>
    </div>
  )
};

export default Admin;
