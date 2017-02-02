using System;
using Android.App;
using Android.Content;
using Android.OS;
using Android.Widget;
using spoty.Services;
using Spoty.Data;
using Spoty.Data.Models;

namespace spoty
{
    [Activity(MainLauncher = true, Icon = "@drawable/logo")]
    public class LoginActivity : Activity
    {
        private Button btnLogin;
        private Button btnLogout;
        private Button btnLoginGuest;
        private Button btnExit;
        private EditText txtUsername;
        private EditText txtPassword;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.LoginLayout);
            FindAllViewsById();
            SetEventHandlers();
        }

        private void FindAllViewsById()
        {
            btnLogin = FindViewById<Button>(Resource.Id.buttonLogin);
            btnLogout = FindViewById<Button>(Resource.Id.buttonLogout);
            btnLoginGuest = FindViewById<Button>(Resource.Id.buttonLoginGuest);
            btnExit = FindViewById<Button>(Resource.Id.buttonExit);
            txtUsername = FindViewById<EditText>(Resource.Id.editTextUsername);
            txtPassword = FindViewById<EditText>(Resource.Id.editTextPassword);
        }

        private void SetEventHandlers()
        {
            btnLogin.Click += BtnLogin_Click;
            btnLogout.Click += BtnLogoutOnClick;
            btnLoginGuest.Click += BtnLoginGuestOnClick;
            btnExit.Click += BtnExitOnClick;
        }

        private void BtnLogoutOnClick(object sender, EventArgs eventArgs)
        {
            btnLogout.Enabled = false;
            Database.Instance.CurrentUser = null;
            btnLogin.Enabled = true;
            btnLoginGuest.Enabled = true;
        }

        private void BtnExitOnClick(object sender, EventArgs eventArgs)
        {
            Process.KillProcess(Process.MyPid());
        }

        private async void BtnLoginGuestOnClick(object sender, EventArgs eventArgs)
        {
            try
            {
                if (await SpotyService.Login(new User(1, "guest", "guest"))) // with await asynchronous methods calling in sprint 2 when we call the auth/login service
                {
                    btnLoginGuest.Enabled = false;
                    btnLogin.Enabled = false;
                    btnLogout.Enabled = true;
                    var activity = new Intent(this, typeof(LocationOverviewActivity));
                    StartActivity(activity);
                }
                else
                {
                    throw new Exception();
                }

            }
            catch (Exception)
            {
                ShowAlertDialog("Error!", "Login was unsuccessful!", "OK");
            }
        }

        private async void BtnLogin_Click(object sender, EventArgs e)
        {
            try
            {
                if (await SpotyService.Login(new User(-1, txtUsername.Text, txtPassword.Text)))
                    // with await asynchronous methods calling in sprint 2 when we call the auth/login service
                {
                    btnLogin.Enabled = false;
                    btnLoginGuest.Enabled = false;
                    btnLogout.Enabled = true;
                    var activity = new Intent(this, typeof(LocationOverviewActivity));
                    StartActivity(activity);
                }
                else
                {
                    throw new Exception();
                }

            }
            catch (Exception)
            {
                ShowAlertDialog("Error!", "Login was unsuccessful!", "OK");
            }
            finally
            {
                txtPassword.Text = "";
                txtUsername.Text = "";
            }
        }

        private void ShowAlertDialog(string title, string message, string posBtnMsg)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.SetTitle(title);
            builder.SetMessage(message);
            builder.SetCancelable(false);
            builder.SetPositiveButton(posBtnMsg, delegate { });
            builder.Show();
        }
    }
}