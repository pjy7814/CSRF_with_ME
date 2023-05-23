/*
input validation에 필요한 util 함수
*/

function validateMemberId(value) {
  // 아이디는 4자 이상, 영문과 숫자만 허용
  return value.length >= 4 && /^[a-zA-Z0-9]+$/.test(value);
}

function validateMemberPassword(value) {
  // 비밀번호는 8자 이상, 영문 대소문자, 숫자, 특수문자를 모두 포함
  return value.length >= 8 && /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&]).*$/.test(value);
}

function validateMemberPasswordCheck(pw, checkpw) {
  return pw == checkpw;
}

function validateMemberEmail(value) {
  // 간단한 이메일 형식
  return /^[\w.-]+@[a-zA-Z_-]+?\.[a-zA-Z]{2,3}$/.test(value);
}

function validateMemberName(value) {
  // 2자 이상
  return value.length >= 2;
}

function validateImgFile(value) {
  return /(image\/jpg|image\/jpeg|image\/png)$/i.test(value);
}

export {
  validateMemberId,
  validateMemberPassword,
  validateMemberPasswordCheck,
  validateMemberEmail,
  validateMemberName,
  validateImgFile,
};
