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
            this.ActionBar.SetTitle(Resource.String.LoginLayoutTitle);
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
            btnLoginGuest.Enabled = true;
        }

        private void BtnExitOnClick(object sender, EventArgs eventArgs)
        {
            Process.KillProcess(Process.MyPid());
        }

        private void BtnLoginGuestOnClick(object sender, EventArgs eventArgs)
        {
            try
            {
                if (SpotyService.Login(new User(1, "guest", "guest"))) // with await asynchronous methods calling in sprint 2 when we call the auth/login service
                {
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
        }

        private void BtnLogin_Click(object sender, EventArgs e)
        {
            ShowAlertDialog("Alert!", "Currently there's only support for guests! \n Use the button below to log in as a guest.", "OK");
            /*
             * 
             * Login with as registered User will be included in Sprint 2, as well as a secure connection to the Service and the the possiblity to register as a new user !
             * 
            btnLogin.Enabled = false;
            btnLoginCancle.Enabled = false;
            if (String.IsNullOrEmpty(txtPassword.Text) || String.IsNullOrEmpty(txtUsername.Text))
            {
                ShowAlertDialog("Error", "Please enter username and password!", "OK");
                btnLogin.Enabled = true;
                btnLoginCancle.Enabled = true;
            }
            else
            {
                /*User user = new User(txtUsername.Text, txtPassword.Text);
                if (await uGuideService.Instance.Login(user))
                {
                    ScanCode();
                }
                else
                {
                    ShowAlertDialog("Error", "Login was unsuccessful!", "OK");
                    btnLogin.Enabled = true;
                    btnLoginCancle.Enabled = true;
                }
            }
            */
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