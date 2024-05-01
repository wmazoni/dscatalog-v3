const tokenKey = 'authData';
type LoginResponse = {
  access_token: string;
  token_type: string;
  expires_in: string;
  scope: string;
  userFirstName: string;
  userId: string;
};
export const saveAuthData = (obj: LoginResponse) => {
  localStorage.setItem(tokenKey, JSON.stringify(obj));
};

export const getAuthData = () => {
  const str = localStorage.getItem(tokenKey) ?? '{}';
  return JSON.parse(str) as LoginResponse;
};

export const removeAuthData = () => {
  localStorage.removeItem(tokenKey);
};
