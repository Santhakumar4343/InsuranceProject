import React, { useState } from 'react';
import axios from 'axios';

const Register = () => {

  const [data, setData] = useState({
    planName: '',
    planStatus: '',
    gender: '',
    startDate: null,
    endDate: null
  });
  const onChangeHandler = e => {
    setData({ ...data, [e.target.name]: e.target.value });
  };
  const onSubmitHandler = async e => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/plans/savePlan", data);
      console.log("Response:", response.data);
    } catch (error) {
      console.error("Error:", error);
    }
  };
  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginTop: '50px' }}>
      <form onSubmit={onSubmitHandler}>
        <table>
          <tbody>
            <tr>
              <td>PlanName</td>
              <td><input type='text' name='planName' value={data.planName} onChange={onChangeHandler}  /></td>
            </tr>
            <tr>
              <td>PlanStatus</td>
              <td><input type='text' name='planStatus' value={data.planStatus} onChange={onChangeHandler} /></td>
            </tr>
            <tr>
              <td>Gender</td>
              <td><input type='text' name='gender' value={data.gender} onChange={onChangeHandler} /></td>
            </tr>
            <tr>
              <td>StartDate</td>
              <td><input type='date' name='startDate' value={data.startDate} onChange={onChangeHandler} /></td>
            </tr>
            <tr>
              <td>EndDate</td>
              <td><input type='date' name='endDate' value={data.endDate} onChange={onChangeHandler} /></td>
            </tr>
            <tr>
              <td></td>
              <td><input type='submit' value="Submit" /></td>
            </tr> 
          </tbody>
        </table>
      </form>
      
    </div>
  );
};
export default Register;
