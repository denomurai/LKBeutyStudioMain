package com.example.lkbeautystore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lkbeautystore.ui.theme.AdminScreens.UpdateEyebrows
import com.example.lkbeautystore.ui.theme.AdminScreens.addEyebrowsScreen
import com.example.lkbeautystore.ui.theme.AdminScreens.addLashesScreen
import com.example.lkbeautystore.ui.theme.AdminScreens.addLipServiceScreen
import com.example.lkbeautystore.ui.theme.AdminScreens.adminDashboard
import com.example.lkbeautystore.ui.theme.AdminScreens.adminLoginScreen
import com.example.lkbeautystore.ui.theme.AdminScreens.adminViewBookingsScreen
import com.example.lkbeautystore.ui.theme.AdminScreens.updateLipServiceScreen
import com.example.lkbeautystore.ui.theme.AdminScreens.viewEyebrowsScreen
import com.example.lkbeautystore.ui.theme.AdminScreens.viewLashesScreen
import com.example.lkbeautystore.ui.theme.AdminScreens.viewLipServicesScreen
import com.example.lkbeautystore.ui.theme.CustomerScreens.bookingEyebrowsScreen
import com.example.lkbeautystore.ui.theme.CustomerScreens.custBookingScreen
import com.example.lkbeautystore.ui.theme.CustomerScreens.custViewEybrowsScreen
import com.example.lkbeautystore.ui.theme.CustomerScreens.custViewLashesScreen
import com.example.lkbeautystore.ui.theme.CustomerScreens.custViewLipScreen
import com.example.lkbeautystore.ui.theme.CustomerScreens.dashBoardScreen
import com.example.lkbeautystore.ui.theme.CustomerScreens.userLogin
import com.example.lkbeautystore.ui.theme.CustomerScreens.userRegistration
import com.example.lkbeautystore.ui.theme.SplashScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH,
    openDrawer: (() -> Unit)? = null,
    scope: CoroutineScope? = null
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // -------------------- USER ROUTES -------------------- //
        composable(ROUTE_USER_REGISTER) {
            userRegistration(navController)
        }

        composable(ROUTE_USER_LOGIN) {
            userLogin(navController)
        }

        composable(ROUTE_DASHBOARD) {
            dashBoardScreen(
                navController = navController,
                openDrawer = openDrawer
            )
        }

        composable(ROUTE_CUST_VEIW_EYEBROS) {
            custViewEybrowsScreen(navController)
        }
        composable (ROUTE_CUST_VIEW_LIP){
            custViewLipScreen(navController) }

        composable (ROUTE_CUST_VIEW_LASHES){ custViewLashesScreen(navController) }

        // Book service with arguments
        composable(
            route = "bookService/{name}/{description}/{amount}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType },
                navArgument("amount") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""
            val amount = backStackEntry.arguments?.getString("amount") ?: ""
            bookingEyebrowsScreen(navController, name, description, amount)
        }

        // -------------------- ADMIN ROUTES -------------------- //
        composable(ROUTE_ADMIN_LOGIN) {
            adminLoginScreen(navController)
        }

        composable(ROUTE_ADMIN_DASHBOARD) {
            adminDashboard(navController)
        }



        composable(ROUTE_ADD_Eyebrows) {
            addEyebrowsScreen(navController)
        }

        composable(ROUTE_ADD_LASHES) {
            addLashesScreen(navController)
        }

        composable(ROUTE_ADD_Lip) {
            addLipServiceScreen(navController)
        }



        composable(
            route = "$ROUTE_ADMIN_UPDATE_LIP/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            updateLipServiceScreen(navController, id) //
        }

        composable(ROUTE_ADMIN_VIEW_EYEBROWS) {
            viewEyebrowsScreen(navController)
        }

        composable(ROUTE_ADMIN_VIEW_EYEBROWS_BOOKINGS){
            adminViewBookingsScreen(navController)
        }

        composable(ROUTE_ADMIN_VIEW_LIP){viewLipServicesScreen(navController)
                   }

        composable(ROUTE_ADMIN_VIEW_LASHES){
            viewLashesScreen(navController)
        }


        composable(
            route = "$ROUTE_ADMIN_UPDATE_Eyebrows/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            UpdateEyebrows(navController, id)
        }

        composable(
            route = "$ROUTE_ADMIN_UPDATE_LASHES/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            UpdateEyebrows(navController, id)
        }

        composable(ROUTE_CUST_BOOKING_HISTORY){
            custBookingScreen(navController)}

        // -------------------- DRAWER ROUTES -------------------- //
        // ✅ DASHBOARD (Drawer works here)
        composable(Routes.Dashboard) {
            dashBoardScreen(
                navController = navController,
                openDrawer = { scope?.launch { openDrawer?.invoke() } }
            )
        }

        composable(Routes.EyebrowsService) {
            custViewEybrowsScreen(navController)
        }

        composable(Routes.BookingHistory) {
            custBookingScreen(navController)
        }

        composable(Routes.Login) {
            userLogin(navController)
        }

        composable(Routes.UserRegister) {
            userRegistration(navController)
        }

        /* composable(ROUTE_ADMIN_LOGIN) {
             adminLoginScreen(navController)
         }*/

        /*composable(
            route = Routes.BookingScreen,
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("desc") { type = NavType.StringType },
                navArgument("amount") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val desc = backStackEntry.arguments?.getString("desc") ?: ""
            val amount = backStackEntry.arguments?.getString("amount") ?: ""
            bookingEyebrowsScreen(navController, name, desc, amount)
        }*/
        composable (ROUTE_SPLASH ){
            SplashScreen(){navController.navigate(ROUTE_USER_LOGIN)
            {popUpTo (ROUTE_SPLASH){inclusive=true} }  }
        }
    }

}