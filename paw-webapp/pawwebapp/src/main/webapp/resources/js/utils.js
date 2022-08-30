class Header extends HTMLElement {
    connectedCallback() {
      this.innerHTML = `
      <header>
    <nav>
      <div class="nav-wrapper custom-nav">
          <a href="index.html" class="brand-logo">CompanyName</a>
          <a href="" class="sidenav-trigger" data-target="mobile-menu">
            <i class="material-icons">menu</i>
            </a>
          <ul id="nav-mobile" class="right hide-on-med-and-down">
              <li style="padding-right:2vw;"><a href="explore.html">Explorar</a></li>
              <li style="padding-right:2vw;"><a href="FAQ.html">FAQ</a></li>
              <li style="padding-right:5vw;"><a href="login.html">Iniciar Sesión/Registrarse</a></li>
          </ul>
          <ul id="modbile-menu" class="sidenav grey lighten-2">
              <li style="padding-right:2vw;"><a href="explore.html">Explorar</a></li>
              <li style="padding-right:2vw;"><a href="FAQ.html">FAQ</a></li>
              <li style="padding-right:5vw;"><a href="login.html">Iniciar Sesión/Registrarse</a></li>
          </ul>
      </div>
  </nav> </header>
          `
    }
  }
  
  //Footer
  
  class Footer extends HTMLElement {
    connectedCallback() {
      this.innerHTML = 
        `
          <!-- Footer -->
  <footer class="page-footer font-small blue pt-4">
  
    <!-- Footer Links -->
    <div class="container-fluid text-center text-md-left">
  
      <!-- Grid row -->
      <div class="row">
  
        <!-- Grid column -->
        <div class="col-md-6 mt-md-0 mt-3">
  
          <!-- Content -->
          <h5 class="text-uppercase">Footer Content</h5>
          <p>Here you can use rows and columns to organize your footer content.</p>
  
        </div>
        <!-- Grid column -->
  
        <hr class="clearfix w-100 d-md-none pb-3">
  
        <!-- Grid column -->
        <div class="col-md-3 mb-md-0 mb-3">
  
          <!-- Links -->
          <h5 class="text-uppercase">Links</h5>
  
          <ul class="list-unstyled">
            <li>
              <a href="#!">Link 1</a>
            </li>
            <li>
              <a href="#!">Link 2</a>
            </li>
            <li>
              <a href="#!">Link 3</a>
            </li>
            <li>
              <a href="#!">Link 4</a>
            </li>
          </ul>
  
        </div>
        <!-- Grid column -->
  
        <!-- Grid column -->
        <div class="col-md-3 mb-md-0 mb-3">
  
          <!-- Links -->
          <h5 class="text-uppercase">Links</h5>
  
          <ul class="list-unstyled">
            <li>
              <a href="#!">Link 1</a>
            </li>
            <li>
              <a href="#!">Link 2</a>
            </li>
            <li>
              <a href="#!">Link 3</a>
            </li>
            <li>
              <a href="#!">Link 4</a>
            </li>
          </ul>
  
        </div>
        <!-- Grid column -->
  
      </div>
      <!-- Grid row -->
  
    </div>
    <!-- Footer Links -->
  
    <!-- Copyright -->
    <div class="footer-copyright text-center py-3">© 2020 Copyright:
      <a href="https://mdbootstrap.com/"> MDBootstrap.com</a>
    </div>
    <!-- Copyright -->
  
  </footer>
  <!-- Footer -->
  
        `
      
    }
  }
  
  customElements.define('main-header', Header);
  customElements.define('main-footer', Footer);