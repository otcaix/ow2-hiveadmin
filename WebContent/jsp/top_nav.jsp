
<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span> 
				</a>
				<a class="brand" style="font-family:georgia,serif;" href="index.jsp"> <img alt="HiveAdmin Logo" src="img/hiveadmin-logo.png" /> <span style="font-style:italic">Hive</span><span style="font-weight:bold">Admin</span></a>
				
				<div class="top-nav nav-collapse pull-right">
					<ul class="nav">
						<li><a href="#">Visit Site</a></li>
					</ul>
				</div>
				
				<!-- user dropdown starts -->
				<div class="btn-group pull-right  " >
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="icon-user"></i><span class="hidden-phone"> <%=session.getAttribute("user") %> </span>
						<span class="caret"></span>
					</a>
					
					<ul class="dropdown-menu">
					
						<li><a href="#">Profile</a></li>
						<li class="divider"></li>
						<li><a href="login.html">Logout</a></li>
					</ul>
				</div>
				<!-- user dropdown ends -->
				
			
			</div>
		</div>
	</div>
	<!-- topbar ends -->