function loginForm() {
   console.log('createAccountForm()  clicked!!!');
   
   let form = document.login_form;
   if (form.u_id.value === '') {
      alert('아이디 입력칸이 비었습니다. 아이디를 입력해주세요');
      form.u_id.focus();
      
   } else if (form.u_pw.value === '') {
      alert('비밀번호 입력칸이 비었습니다. 비밀번호를 입력해주세요');
      form.u_pw.focus();
      
   }else {
      form.submit();
      
   }
}





