import React, { useRef } from "react";
import { useLocation } from "react-router";

const Home = () => {
  const location = useLocation();
  var {userToken, from, shelterName, role } = location.state || {};
  // const [homeInfo, setHomeInfo] = useState<HomeInfo | null>(null);
  const isMounted = useRef<boolean>(true);
  return (
    <>
      <h3>Token : {userToken}</h3>
      <h3>From : {from}</h3>
      <h3>Shelter Name : {shelterName}</h3>
      <h3>Role: {role}</h3>
    </>
  );
};

export default Home;
